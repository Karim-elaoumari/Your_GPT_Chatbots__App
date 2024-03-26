import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenStorageService } from '../services/TokenStorageService';

@Injectable({
  providedIn: 'root'
})
export class AuthenticatedGuard implements CanActivate {
  constructor(private tokenStorageService:TokenStorageService,private router:Router) { }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      const user = this.tokenStorageService.getUser();
      const refresh = this.tokenStorageService.getRefreshToken();
    if(user == null || user == undefined  || refresh == null || refresh == undefined){
      return true;
    }else{
      this.router.navigate(['/home']);
      return false;
    }
  }
  
}
