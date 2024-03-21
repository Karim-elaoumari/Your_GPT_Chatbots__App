import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { env } from "src/app/environments/environment";

@Injectable(
    {
        providedIn: 'root'
    }
)
export class ConversationService {
    constructor(private http:HttpClient, ) { }
    private readonly api:string = env.api;
    getConversations(chatbotId: string,page:number,size:number): Observable<any> {
        return this.http.get(this.api + '/api/v1/analytics/chatbot/chat-entries/'+chatbotId+'?page='+page+'&size='+size);
    }
}

