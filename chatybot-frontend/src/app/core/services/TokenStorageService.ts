import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { EncrDecrService } from './EncrDecrService';
import { env } from "src/app/environments/environment";
import { UserResponseInfo } from '../models/UserResponseInfo';
import { UserResponse } from '../models/UserResponse';

const TOKEN_KEY = 'auth-token';
const REFRESHTOKEN_KEY = 'auth-refreshtoken';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  constructor(private cookieService:CookieService,
    private encrDecrService:EncrDecrService
    ) { }

    private readonly key:string = env.key;

  signOut(): void {
    window.sessionStorage.clear();
    this.cookieService.delete(TOKEN_KEY);
    this.cookieService.delete(REFRESHTOKEN_KEY);
  }

  public saveToken(token: string): void {

    this.cookieService.delete(TOKEN_KEY);
    this.cookieService.set(TOKEN_KEY, this.encrDecrService.set(this.key,token));
  }

  public getToken(): string | null {
    if(this.cookieService.get(TOKEN_KEY)!==null){
      return this.encrDecrService.get(
        this.key,
        this.cookieService.get(TOKEN_KEY)
      );
    }
    return null;
  }

  public saveRefreshToken(token: string): void {
    this.cookieService.delete(REFRESHTOKEN_KEY);
    this.cookieService.set(
      REFRESHTOKEN_KEY, 
      this.encrDecrService.set(this.key,token)
      );
  }

  public getRefreshToken(): string | null {
    if(this.cookieService.get(REFRESHTOKEN_KEY)!==null){
      return this.encrDecrService.get(
        this.key,
        this.cookieService.get(REFRESHTOKEN_KEY)
      );
    }
    return null;
  }

  public saveUser(user: any): void {
    window.sessionStorage.removeItem(USER_KEY);
    let user_info:UserResponseInfo = {
      id: user.id,
      email: user.email,
      username: user.username,
      role: user.role,
      firstName: user.firstName,
      lastName: user.lastName,
      picture: user.picture,
      provider: user.provider
    } as UserResponseInfo;
    window.sessionStorage.setItem(
      USER_KEY, 
      this.encrDecrService.set(this.key,JSON.stringify(user_info))
      );
  }

  public getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user!=null && user!='' && user!=undefined) {
      const user_not_parsed = this.encrDecrService.get(this.key,user);
      const user_parsed:UserResponseInfo =    JSON.parse(user_not_parsed); 
      return user_parsed;
      
    }
    return null;
  }
}