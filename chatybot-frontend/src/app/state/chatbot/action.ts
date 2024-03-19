
import { Action } from "@ngrx/store";

export enum ChatBotActionTypes {
    LOAD_CHATBOT = '[ChatBot] Load ChatBot',
    LOAD_CHATBOT_SUCCESS = '[ChatBot] Load ChatBot Success',
    LOAD_CHATBOT_FAILURE = '[ChatBot] Load ChatBot Failure',
    LOAD_CHATBOTS = '[ChatBot] Load ChatBots',
    LOAD_CHATBOTS_SUCCESS = '[ChatBot] Load ChatBots Success',
    LOAD_CHATBOTS_FAILURE = '[ChatBot] Load ChatBots Failure',
    CREATE_CHATBOT = '[ChatBot] Create ChatBot',
    CREATE_CHATBOT_SUCCESS = '[ChatBot] Create ChatBot Success',
    CREATE_CHATBOT_FAILURE = '[ChatBot] Create ChatBot Failure',
    UPDATE_CHATBOT = '[ChatBot] Update ChatBot',
    UPDATE_CHATBOT_SUCCESS = '[ChatBot] Update ChatBot Success',
    UPDATE_CHATBOT_FAILURE = '[ChatBot] Update ChatBot Failure',
    DELETE_CHATBOT = '[ChatBot] Delete ChatBot',
    DELETE_CHATBOT_SUCCESS = '[ChatBot] Delete ChatBot Success',
    DELETE_CHATBOT_FAILURE = '[ChatBot] Delete ChatBot Failure',
    // data caracters 
    LOAD_CHATBOT_DATA_CARACTERS = '[ChatBot] Load ChatBot Data Caracters',
    LOAD_CHATBOT_DATA_CARACTERS_SUCCESS = '[ChatBot] Load ChatBot Data Caracters Success',
    LOAD_CHATBOT_DATA_CARACTERS_FAILURE = '[ChatBot] Load ChatBot Data Caracters Failure',

    // add origin 
    CREATE_CHATBOT_ORIGIN = '[ChatBot] Create ChatBot Origin',
    CREATE_CHATBOT_ORIGIN_SUCCESS = '[ChatBot] Create ChatBot Origin Success',
    CREATE_CHATBOT_ORIGIN_FAILURE = '[ChatBot] Create ChatBot Origin Failure',
    // delete origin
    DELETE_CHATBOT_ORIGIN = '[ChatBot] Delete ChatBot Origin',
    DELETE_CHATBOT_ORIGIN_SUCCESS = '[ChatBot] Delete ChatBot Origin Success',
    DELETE_CHATBOT_ORIGIN_FAILURE = '[ChatBot] Delete ChatBot Origin Failure',
}
export class LoadChatBot implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.LOAD_CHATBOT;
    constructor(public payload: any) {
    }
}
export class LoadChatBotSuccess implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.LOAD_CHATBOT_SUCCESS;
    constructor(public payload: any) {
    }
}
export class LoadChatBotFailure implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.LOAD_CHATBOT_FAILURE;
    constructor(public payload: string) {
    }
}
export class LoadChatBots implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.LOAD_CHATBOTS;
    constructor(public payload?: any) {
    }
}
export class LoadChatBotsSuccess implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.LOAD_CHATBOTS_SUCCESS;
    constructor(public payload: any) {
    }
}
export class LoadChatBotsFailure implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.LOAD_CHATBOTS_FAILURE;
    constructor(public payload: string) {
    }
}
export class CreateChatBot implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.CREATE_CHATBOT;
    constructor(public payload: any) {
    }
}
export class CreateChatBotSuccess implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.CREATE_CHATBOT_SUCCESS;
    constructor(public payload: any) {
    }
}
export class CreateChatBotFailure implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.CREATE_CHATBOT_FAILURE;
    constructor(public payload: string) {
    }
}
export class UpdateChatBot implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.UPDATE_CHATBOT;
    constructor(public payload: any) {
    }
}
export class UpdateChatBotSuccess implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.UPDATE_CHATBOT_SUCCESS;
    constructor(public payload: any) {
    }
}
export class UpdateChatBotFailure implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.UPDATE_CHATBOT_FAILURE;
    constructor(public payload: string) {
    }
}
export class DeleteChatBot implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.DELETE_CHATBOT;
    constructor(public payload: any) {
    }
}
export class DeleteChatBotSuccess implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.DELETE_CHATBOT_SUCCESS;
    constructor(public payload: any) {
    }
}
export class DeleteChatBotFailure implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.DELETE_CHATBOT_FAILURE;
    constructor(public payload: string) {
    }
}
// data caracters
export class LoadChatBotDataCaracters implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.LOAD_CHATBOT_DATA_CARACTERS;
    constructor(public payload: any) {
    }
}
export class LoadChatBotDataCaractersSuccess implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.LOAD_CHATBOT_DATA_CARACTERS_SUCCESS;
    constructor(public payload: any) {
    }
}
export class LoadChatBotDataCaractersFailure implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.LOAD_CHATBOT_DATA_CARACTERS_FAILURE;
    constructor(public payload: string) {
    }
}
// add origin
export class CreateChatBotOrigin implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.CREATE_CHATBOT_ORIGIN;
    constructor(public payload: any) {
    }
}
export class CreateChatBotOriginSuccess implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.CREATE_CHATBOT_ORIGIN_SUCCESS;
    constructor(public payload: any) {
    }
}
export class CreateChatBotOriginFailure implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.CREATE_CHATBOT_ORIGIN_FAILURE;
    constructor(public payload: string) {
    }
}
// delete origin
export class DeleteChatBotOrigin implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.DELETE_CHATBOT_ORIGIN;
    constructor(public payload: {chat_bot_id:string,origin:string}) {
    }
}
export class DeleteChatBotOriginSuccess implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.DELETE_CHATBOT_ORIGIN_SUCCESS;
    constructor(public payload: any) {
    }
}
export class DeleteChatBotOriginFailure implements Action {
    readonly type: ChatBotActionTypes  = ChatBotActionTypes.DELETE_CHATBOT_ORIGIN_FAILURE;
    constructor(public payload: string) {
    }
}
export type ChatBotActions = LoadChatBot | LoadChatBotSuccess | LoadChatBotFailure | LoadChatBots | LoadChatBotsSuccess | LoadChatBotsFailure | CreateChatBot | CreateChatBotSuccess | CreateChatBotFailure | UpdateChatBot | UpdateChatBotSuccess | UpdateChatBotFailure | DeleteChatBot | DeleteChatBotSuccess | DeleteChatBotFailure | LoadChatBotDataCaracters | LoadChatBotDataCaractersSuccess | LoadChatBotDataCaractersFailure | CreateChatBotOrigin | CreateChatBotOriginSuccess | CreateChatBotOriginFailure | DeleteChatBotOrigin | DeleteChatBotOriginSuccess | DeleteChatBotOriginFailure;