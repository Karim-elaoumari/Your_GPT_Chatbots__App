import { Injectable } from '@angular/core';
import { EncrDecrService } from './EncrDecrService';
import { env } from "src/app/environments/environment";
import { UserResponseInfo } from '../models/UserResponseInfo';

const TOKEN_KEY = 'auth-token';
const REFRESHTOKEN_KEY = 'auth-refreshtoken';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  constructor(
    private encrDecrService:EncrDecrService
    ) { }

    private readonly key:string = env.key;

  signOut(): void {
    localStorage.clear();
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(REFRESHTOKEN_KEY);
  }

  public saveToken(token: string): void {

    localStorage.removeItem(TOKEN_KEY);
    localStorage.setItem(TOKEN_KEY, this.encrDecrService.set(this.key,token));
  }

  public getToken(): string | null {
    let token_key = localStorage.getItem(TOKEN_KEY);
    if(token_key!==null){
      return this.encrDecrService.get(
        this.key,
        token_key
      );
    }
    return null;
  }

  public saveRefreshToken(token: string): void {
    localStorage.removeItem(REFRESHTOKEN_KEY);
    localStorage.setItem(
      REFRESHTOKEN_KEY, 
      this.encrDecrService.set(this.key,token)
      );
  }

  public getRefreshToken(): string | null {
    let token_key = localStorage.getItem(REFRESHTOKEN_KEY);
    if(token_key!==null){
      return this.encrDecrService.get(
        this.key,
        token_key
      );
    }
    return null;
  }

  public saveUser(user: any): void {
    localStorage.removeItem(USER_KEY);
    let user_info:UserResponseInfo = {
      id: user.id,
      email: user.email,
      username: user.username,
      role: user.role,
      firstName: user.firstName,
      lastName: user.lastName,
      picture: user.picture,
      subscription: user.subscription,
      provider: user.provider
    } as UserResponseInfo;
    localStorage.setItem(
      USER_KEY, 
      this.encrDecrService.set(this.key,JSON.stringify(user_info))
      );
  }

  public getUser(): any {
    const user = localStorage.getItem(USER_KEY);
    if (user!=null && user!='' && user!=undefined) {
      const user_not_parsed = this.encrDecrService.get(this.key,user);
      const user_parsed:UserResponseInfo =    JSON.parse(user_not_parsed); 
      return user_parsed;
      
    }
    return null;
  }
}