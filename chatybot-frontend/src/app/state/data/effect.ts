import { Injectable } from '@angular/core';
import { Actions, act, createEffect, ofType } from '@ngrx/effects';
import { Observable, of } from 'rxjs';
import { catchError, map, switchMap } from 'rxjs/operators';
import { DataService } from 'src/app/core/services/DataService';
import { Action } from '@ngrx/store';
import { DataActionTypes } from './action';
import { ConversationService } from 'src/app/core/services/ConversationService';

@Injectable()
export class DataEffects {
    constructor(
        private actions$: Actions,
        private dataService: DataService,
        private conversationService: ConversationService
    ) {}
    loadData$: Observable<Action> = createEffect(() =>
        this.actions$.pipe(
            ofType(DataActionTypes.LOAD_DATA),
            switchMap((action: { type: string, payload: {chat_bot_id:string,page:number,size:number}}) => 
                this.dataService.getData(action.payload.chat_bot_id,action.payload.page,action.payload.size).pipe(
                    map((chat_bot: any) => ({
                        type: DataActionTypes.LOAD_DATA_SUCCESS,
                        payload: chat_bot.data
                    })),
                    catchError(err => of({
                        type: DataActionTypes.LOAD_DATA_FAILURE,
                        payload: err
                    }))
                )
            )
        )
    );
    createTextData$: Observable<Action> = createEffect(() =>
        this.actions$.pipe(
            ofType(DataActionTypes.CREATE_DATA),
            switchMap((action: { type: string, payload: any }) => 
                this.dataService.createTextData(action.payload).pipe(
                    map((chat_bot: any) => ({
                        type: DataActionTypes.CREATE_DATA_SUCCESS,
                        payload: chat_bot.data
                    })),
                    catchError(err => of({
                        type: DataActionTypes.CREATE_DATA_FAILURE,
                        payload: err
                    }))
                )
            )
        )
    );
    createFileData$: Observable<Action> = createEffect(() =>
        this.actions$.pipe(
            ofType(DataActionTypes.CREATE_FILE_DATA),
            switchMap((action: { type: string, payload: {chat_bot_id:string,file:File} }) => 
                this.dataService.createFileData(action.payload.file,action.payload.chat_bot_id).pipe(
                    map((chat_bot: any) => ({
                        type: DataActionTypes.CREATE_FILE_DATA_SUCCESS,
                        payload: chat_bot.data
                    })),
                    catchError(err => of({
                        type: DataActionTypes.CREATE_FILE_DATA_FAILURE,
                        payload: err
                    }))
                )
            )
        )
    );
    deleteData$: Observable<Action> = createEffect(() =>
        this.actions$.pipe(
            ofType(DataActionTypes.DELETE_DATA),
            switchMap((action: { type: string, payload: string }) => 
                this.dataService.deleteData(action.payload).pipe(
                    map((chat_bot: any) => ({
                        type: DataActionTypes.DELETE_DATA_SUCCESS,
                        payload: action.payload
                    })),
                    catchError(err => of({
                        type: DataActionTypes.DELETE_DATA_FAILURE,
                        payload: err
                    }))
                )
            )
        )
    );
    // conversation
    loadConversations$: Observable<Action> = createEffect(() =>
        this.actions$.pipe(
            ofType(DataActionTypes.LOAD_CONVERSATIONS),
            switchMap((action: { type: string, payload: {chat_bot_id:string,page:number,size:number}}) => 
                this.conversationService.getConversations(action.payload.chat_bot_id,action.payload.page,action.payload.size).pipe(
                    map((chat_bot: any) => ({
                        type: DataActionTypes.LOAD_CONVERSATIONS_SUCCESS,
                        payload: chat_bot.data
                    })),
                    catchError(err => of({
                        type: DataActionTypes.LOAD_CONVERSATIONS_FAILURE,
                        payload: err
                    }))
                )
            )
        )
    );

    

    
    
    

    
}