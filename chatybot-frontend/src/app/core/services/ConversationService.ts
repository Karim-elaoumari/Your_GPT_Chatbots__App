import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { env } from "src/app/environments/environment";

@Injectable(
    {
        providedIn: 'root'
    }
)
export class ConversationService {
    constructor(private http:HttpClient, ) { }
    private readonly api:string = env.api;


}

