import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, filter, switchMap, take } from 'rxjs/operators';
import { TokenStorageService } from '../services/TokenStorageService';
import { AuthService } from '../services/AuthService';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  
  private isRefreshing = false;
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

  constructor(private tokenService: TokenStorageService, private authService: AuthService,private router:Router) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<Object>> {
    let authReq = req;
    const token = this.tokenService.getToken();
    if(!req.url.includes('auth/login') && !req.url.includes('auth/register' ) && !req.url.includes('auth/refresh' ) &&  !req.url.includes('main/prompt') ){
        console.log('Intercepted')
        if (token != null && token != undefined && token != '') {
          console.log('Token found');
          authReq = this.addTokenHeader(req, token);
        }
        return next.handle(authReq).pipe(
          catchError(error => { 
          if (error instanceof HttpErrorResponse && !authReq.url.includes('auth') && error.status == 401) {
            console.log('Error 401');
            return this.handle401Error(authReq, next);
            // return next.handle(req)
          }
          return throwError(error);
        }));
    }else{
      return next.handle(authReq);
    }
  }

  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.refreshTokenSubject.next(null);

      const token_ref = this.tokenService.getRefreshToken();
      console.log(token_ref)
      if (token_ref){
       
        return this.authService.refreshToken().pipe(
          switchMap((response: any) => {
            console.log('Token Refreshed');
            this.isRefreshing = false;
            this.tokenService.saveToken(response.accessToken);
            this.refreshTokenSubject.next(response.accessToken);
            return next.handle(this.addTokenHeader(request, response.accessToken));
          }),
          catchError((err) => {
            this.isRefreshing = false;
            this.tokenService.signOut();
            console.log('Error while refreshing token');
            this.router.navigate(['/login']);
            return throwError(err);
          })
        );
      } 
      }
    // else{
    //   console.log('Error while refreshing token');
    // }
    return this.refreshTokenSubject.pipe(
      filter(token => token !== null),
      take(1),
      switchMap((token) => next.handle(this.addTokenHeader(request, token)))
    );
  }
  private addTokenHeader(request: HttpRequest<any>, token: string) {
    return request.clone({
      setHeaders: {
          Authorization: `Bearer ${token}`
      }
  });

  }
}