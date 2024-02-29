import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Route, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenStorageService } from '../services/TokenStorageService';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private tokenStorageService:TokenStorageService,private router:Router) {}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      const user = this.tokenStorageService.getUser();
      const refresh = this.tokenStorageService.getRefreshToken();
    if(user == null || user == undefined  || refresh == null || refresh == undefined){
      this.router.navigate(['/login']);
      this.tokenStorageService.signOut();
      return false;
    }else return true;
  }
  
}
