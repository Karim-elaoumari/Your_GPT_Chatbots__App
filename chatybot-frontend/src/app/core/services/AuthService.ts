import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { UserRegister } from "../models/UserRegister";
import { env } from "src/app/environments/environment";

@Injectable(
    {
        providedIn: 'root'
    }
)
export class AuthService {
    private readonly api:string = env.api;
    constructor(private http:HttpClient) { }
    login(email: string, password: string): Observable<any>{
        return this.http.post(this.api+'/api/v1/auth/login', {email, password});
    }
    register(data:UserRegister):Observable<any> {
        return this.http.post(this.api+'/api/v1/auth/register', data);
    }
    getUserInfo():Observable<any>{
        return this.http.get(this.api+'/api/v1/auth/me');
    }
    refreshToken(token:string):Observable<any>{
        return this.http.post(this.api+'/api/v1/auth/refresh-token', {'refresh_token': token});
    }
}