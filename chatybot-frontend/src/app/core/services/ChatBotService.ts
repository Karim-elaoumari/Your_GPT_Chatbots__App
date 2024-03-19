import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { env } from "src/app/environments/environment";
import { QuestionRequest } from "../models/QuestionRequest";
import { Observable } from "rxjs";
import { ChatBot } from "../models/ChatBot";

@Injectable(
    {
        providedIn: 'root'
    }
)
export class ChatBotService {
    constructor(private http:HttpClient, ) { }
    private readonly api:string = env.api;
    private readonly key:string  = env.key;
    getChatBotInterface(chatBotId:string, origin:string): Observable<any>{
        origin = btoa(origin);
        return this.http.get(this.api+'/api/v1/main/chatbot/ui',
        {params:{chatBotId, origin}});
    }
    sendMessage(request:QuestionRequest):Observable<any>{
        request.origin = btoa(request.origin);
        console.log(request);
        return this.http.post(this.api + '/api/v1/main/prompt', request);
    }
    getMyChatBots():Observable<any>{
        return this.http.get(this.api + '/api/v1/main/chatbot/my');
    }
    getChatBotDetails(chatBotId:string):Observable<any>{
        return this.http.get(this.api + '/api/v1/main/chatbot/'+chatBotId);
    }
    getChatBotDataCharacters(chatBotId:string):Observable<any>{
        return this.http.get(this.api + '/api/v1/analytics/chatbot/data-characters/'+chatBotId);
    }
    updateChatBot(chatBot:ChatBot):Observable<any>{
        return this.http.put(this.api + '/api/v1/main/chatbot', chatBot);
    }
    createChatBot(name:string):Observable<any>{
        return this.http.post(this.api + '/api/v1/main/chatbot', {name: name});
    }
    deleteChatBot(chat_id:string):Observable<any>{
        return this.http.delete(this.api+'/api/v1/main/chatbot/'+chat_id);
    }
}