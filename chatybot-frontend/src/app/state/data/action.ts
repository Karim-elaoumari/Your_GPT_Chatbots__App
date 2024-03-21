import { Action } from "@ngrx/store";

export enum DataActionTypes {
    LOAD_DATA = '[Data] Load Data',
    LOAD_DATA_SUCCESS = '[Data] Load Data Success',
    LOAD_DATA_FAILURE = '[Data] Load Data Failure',
    CREATE_DATA = '[Data] Create Data',
    CREATE_DATA_SUCCESS = '[Data] Create Data Success',
    CREATE_DATA_FAILURE = '[Data] Create Data Failure',
    CREATE_FILE_DATA = '[Data] Create File Data',
    CREATE_FILE_DATA_SUCCESS = '[Data] Create File Data Success',
    CREATE_FILE_DATA_FAILURE = '[Data] Create File Data Failure',
    DELETE_DATA = '[Data] Delete Data',
    DELETE_DATA_SUCCESS = '[Data] Delete Data Success',
    DELETE_DATA_FAILURE = '[Data] Delete Data Failure',
    // conversation
    LOAD_CONVERSATIONS = '[Data] Load Conversations',
    LOAD_CONVERSATIONS_SUCCESS = '[Data] Load Conversations Success',
    LOAD_CONVERSATIONS_FAILURE = '[Data] Load Conversations Failure',
}
export class LoadData implements Action {
    readonly type: DataActionTypes  = DataActionTypes.LOAD_DATA;
    constructor(public payload?: any) {
    }
}
export class LoadDataSuccess implements Action {
    readonly type: DataActionTypes  = DataActionTypes.LOAD_DATA_SUCCESS;
    constructor(public payload: any) {
    }
}
export class LoadDataFailure implements Action {
    readonly type: DataActionTypes  = DataActionTypes.LOAD_DATA_FAILURE;
    constructor(public payload: string) {
    }
}
export class CreateData implements Action {
    readonly type: DataActionTypes  = DataActionTypes.CREATE_DATA;
    constructor(public payload: any) {
    }
}
export class CreateDataSuccess implements Action {
    readonly type: DataActionTypes  = DataActionTypes.CREATE_DATA_SUCCESS;
    constructor(public payload: any) {
    }
}
export class CreateDataFailure implements Action {
    readonly type: DataActionTypes  = DataActionTypes.CREATE_DATA_FAILURE;
    constructor(public payload: string) {
    }
}
export class CreateFileData implements Action {
    readonly type: DataActionTypes  = DataActionTypes.CREATE_FILE_DATA;
    constructor(public payload: {chat_bot_id:string,file:File}) {
    }
}
export class CreateFileDataSuccess implements Action {
    readonly type: DataActionTypes  = DataActionTypes.CREATE_FILE_DATA_SUCCESS;
    constructor(public payload: any) {
    }
}
export class CreateFileDataFailure implements Action {
    readonly type: DataActionTypes  = DataActionTypes.CREATE_FILE_DATA_FAILURE;
    constructor(public payload: string) {
    }
}
export class DeleteData implements Action {
    readonly type: DataActionTypes  = DataActionTypes.DELETE_DATA;
    constructor(public payload: any) {
    }
}
export class DeleteDataSuccess implements Action {
    readonly type: DataActionTypes  = DataActionTypes.DELETE_DATA_SUCCESS;
    constructor(public payload: any) {
    }
}
export class DeleteDataFailure implements Action {
    readonly type: DataActionTypes  = DataActionTypes.DELETE_DATA_FAILURE;
    constructor(public payload: string) {
    }
}
export class LoadConversations implements Action {
    readonly type: DataActionTypes  = DataActionTypes.LOAD_CONVERSATIONS;
    constructor(public payload?: any) {
    }
}
export class LoadConversationsSuccess implements Action {
    readonly type: DataActionTypes  = DataActionTypes.LOAD_CONVERSATIONS_SUCCESS;
    constructor(public payload: any) {
    }
}
export class LoadConversationsFailure implements Action {
    readonly type: DataActionTypes  = DataActionTypes.LOAD_CONVERSATIONS_FAILURE;
    constructor(public payload: string) {
    }
}