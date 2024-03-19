import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { env } from "src/app/environments/environment";

@Injectable(
    {
        providedIn: 'root'
    }
)
export class OriginsService {
    constructor(private http:HttpClient, ) { }
    private readonly api:string = env.api;
    addOrigin(chat_bot_id:string, originName:string): Observable<any>{
  return this.http.post(this.api + '/api/v1/main/chatbot/allowed-origins/' + chat_bot_id, originName);
    }
    deleteOrigin(chat_bot_id:string, origin:string): Observable<any>{
        return this.http.delete(this.api + '/api/v1/main/chatbot/allowed-origins/'+chat_bot_id, {params:{origin:origin}});
    }


}

