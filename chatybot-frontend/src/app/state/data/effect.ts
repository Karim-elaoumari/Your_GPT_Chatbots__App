import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Observable, of } from 'rxjs';
import { catchError, map, switchMap } from 'rxjs/operators';
import { DataService } from 'src/app/core/services/DataService';
import { Action } from '@ngrx/store';
import { DataActionTypes } from './action';

@Injectable()
export class DataEffects {
    constructor(
        private actions$: Actions,
        private dataService: DataService
    ) {}
    loadData$: Observable<Action> = createEffect(() =>
        this.actions$.pipe(
            ofType(DataActionTypes.LOAD_DATA),
            switchMap((action: { type: string, payload: string }) => 
                this.dataService.getData(action.payload).pipe(
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
                        payload: chat_bot.data
                    })),
                    catchError(err => of({
                        type: DataActionTypes.DELETE_DATA_FAILURE,
                        payload: err
                    }))
                )
            )
        )
    );

    

    
    
    

    
}