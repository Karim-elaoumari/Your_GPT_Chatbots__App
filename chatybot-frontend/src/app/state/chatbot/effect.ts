import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { Observable, of } from "rxjs";
import { switchMap, map, catchError } from "rxjs/operators"; // Add necessary import
import { ChatBotService } from "src/app/core/services/ChatBotService";
import { ChatBotActionTypes } from "./action";
import { Action } from "@ngrx/store";
import { ChatBot } from "src/app/core/models/ChatBot";
import { OriginsService } from "src/app/core/services/OriginsService";

@Injectable()
export class ChatBotEffects {
    constructor(
        public chatBotService: ChatBotService,
        public effectActions$: Actions,
        public originsService: OriginsService
    ) {}

    loadChatBots: Observable<Action> = createEffect(() => 
        this.effectActions$.pipe(
            ofType(ChatBotActionTypes.LOAD_CHATBOTS),
            switchMap(() =>
                this.chatBotService.getMyChatBots().pipe(
                    map((chat_bots: any) => ({
                        type: ChatBotActionTypes.LOAD_CHATBOTS_SUCCESS,
                        payload: chat_bots.data
                    })),
                    catchError(err => of({
                        type: ChatBotActionTypes.LOAD_CHATBOTS_FAILURE,
                        payload: err
                    }))
                )
            )
        )
    );
    loadChatBot$: Observable<Action> = createEffect(() =>
        this.effectActions$.pipe(
            ofType(ChatBotActionTypes.LOAD_CHATBOT),
            switchMap((action: { type: string, payload: string }) => 
                this.chatBotService.getChatBotDetails(action.payload).pipe(
                    map((chat_bot: any) => ({
                        type: ChatBotActionTypes.LOAD_CHATBOT_SUCCESS,
                        payload: chat_bot.data
                    })),
                    catchError(err => of({
                        type: ChatBotActionTypes.LOAD_CHATBOT_FAILURE,
                        payload: err
                    }))
                )
            )
        )
    );
    loadChatBotDataCaracters$: Observable<Action> = createEffect(() =>
        this.effectActions$.pipe(
            ofType(ChatBotActionTypes.LOAD_CHATBOT_DATA_CARACTERS),
            switchMap((action: { type: string, payload: string }) => 
                this.chatBotService.getChatBotDataCharacters(action.payload).pipe(
                    map((chat_bot: any) => ({
                        type: ChatBotActionTypes.LOAD_CHATBOT_DATA_CARACTERS_SUCCESS,
                        payload: chat_bot.data
                    })),
                    catchError(err => of({
                        type: ChatBotActionTypes.LOAD_CHATBOT_DATA_CARACTERS_FAILURE,
                        payload: err
                    }))
                )
            )
        )
    );
    // update chatbot
    updateChatBot$: Observable<Action> = createEffect(() =>
        this.effectActions$.pipe(
            ofType(ChatBotActionTypes.UPDATE_CHATBOT),
            switchMap((action: { type: string, payload: ChatBot }) => 
                this.chatBotService.updateChatBot(action.payload).pipe(
                    map((chat_bot: any) => ({
                        type: ChatBotActionTypes.UPDATE_CHATBOT_SUCCESS,
                        payload: chat_bot.data
                    })),
                    catchError(err => of({
                        type: ChatBotActionTypes.UPDATE_CHATBOT_FAILURE,
                        payload: err
                    }))
                )
            )
        )
    );
    // create chatbot origin
    createChatBotOrigin$: Observable<Action> = createEffect(() =>
        this.effectActions$.pipe(
            ofType(ChatBotActionTypes.CREATE_CHATBOT_ORIGIN),
            switchMap((action: { type: string, payload: {chat_bot_id:string,origin:string} }) => 
                this.originsService.addOrigin(action.payload.chat_bot_id,action.payload.origin).pipe(
                    map((origin: any) => ({
                        type: ChatBotActionTypes.CREATE_CHATBOT_ORIGIN_SUCCESS,
                        payload: origin.data
                    })),
                    catchError(err => of({
                        type: ChatBotActionTypes.CREATE_CHATBOT_ORIGIN_FAILURE,
                        payload: err
                    }))
                )
            )
        )
    );

    deleteChatBotOrigin$: Observable<Action> = createEffect(() => 
       
        this.effectActions$.pipe(
            ofType(ChatBotActionTypes.DELETE_CHATBOT_ORIGIN),
            switchMap((action: { type: string, payload: {chat_bot_id:string,origin:string} }) => 
                this.originsService.deleteOrigin(action.payload.chat_bot_id,action.payload.origin).pipe(
                    map((origin: any) => ({
                        type: ChatBotActionTypes.DELETE_CHATBOT_ORIGIN_SUCCESS,
                        payload: action.payload.origin
                    })),
                    catchError(err => of({
                        type: ChatBotActionTypes.DELETE_CHATBOT_ORIGIN_FAILURE,
                        payload: err
                    }))
                )
            )
        )
    );
    // create chatbot
    createChatBot$: Observable<Action> = createEffect(() =>
        this.effectActions$.pipe(
            ofType(ChatBotActionTypes.CREATE_CHATBOT),
            switchMap((action: { type: string, payload: string }) => 
                this.chatBotService.createChatBot(action.payload).pipe(
                    map((chat_bot: any) => ({
                        type: ChatBotActionTypes.CREATE_CHATBOT_SUCCESS,
                        payload: chat_bot.data
                    })),
                    catchError(err => of({
                        type: ChatBotActionTypes.CREATE_CHATBOT_FAILURE,
                        payload: err
                    }))
                )
            )
        )
    );

    // delete chatbot

    deleteChatBot$: Observable<Action> = createEffect(() => 
       
    this.effectActions$.pipe(
        ofType(ChatBotActionTypes.DELETE_CHATBOT),
        switchMap((action: { type: string, payload: string }) => 
            this.chatBotService.deleteChatBot(action.payload).pipe(
                map((response: any) => ({
                    type: ChatBotActionTypes.DELETE_CHATBOT_SUCCESS,
                    payload: action.payload
                })),
                catchError(err => of({
                    type: ChatBotActionTypes.DELETE_CHATBOT_FAILURE,
                    payload: err
                }))
            )
        )
    )
);
}
