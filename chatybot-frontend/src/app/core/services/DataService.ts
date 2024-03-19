import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { env } from "src/app/environments/environment";
import { BotTextdata } from "../models/BotTextData";

@Injectable(
    {
        providedIn: 'root'
    }
)
export class DataService {
    constructor(private http:HttpClient) { }
    private readonly api:string = env.api;
    getData(chatbotId: string): Observable<any> {
        console.log('chatbotId', chatbotId);
        return this.http.get(this.api + '/api/v1/analytics/chatbot/data/'+chatbotId);
    }
    createTextData(data:BotTextdata): Observable<any> {
        return this.http.post(this.api + '/api/v1/main/embed/text', data);
    }
    deleteData(data_id:string): Observable<any> {
        return this.http.delete(this.api + '/api/v1/analytics/chatbot/data/'+data_id);
    }
    createFileData(file: File, chatBotId: string): Observable<any> {
        const formData = new FormData();
        formData.append('file', file);
        return this.http.post(this.api + '/api/v1/main/embed/document/' + chatBotId, formData);
    }



}

