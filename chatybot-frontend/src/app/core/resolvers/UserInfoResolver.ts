import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from "@angular/router";
import { Observable } from "rxjs";
import { AuthService } from "../services/AuthService";
import { TokenStorageService } from "../services/TokenStorageService";
import { UserResponseInfo } from "../models/UserResponseInfo";
// import { catchError } from 'rxjs/operators';
// import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserInfoResolver implements Resolve<any> {
  constructor(
    private authService: AuthService,
    private tokenStorageService: TokenStorageService
  ) { }

  async resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<any> {
    const user: UserResponseInfo = this.tokenStorageService.getUser();
    alert('UserInfoResolver');
    if (user == null) {
      try {
        const response: any = await this.authService.getUserInfo().toPromise();

        if (response && response.statusCode === 200) {
          this.tokenStorageService.saveUser(response.data);
          return response.data;
        }
      } catch (error) {
        console.error('Error fetching user info:', error);
        return null;
      }
    } else {
      return user;
    }
  }
}
