import { ChatBotActionTypes } from "./action";
import { ChatbotState, LoadingStatus, initialState } from "./state";

export function chatbotsReducer(state:ChatbotState = initialState, action:any):ChatbotState {
    switch (action.type) {
        case ChatBotActionTypes.LOAD_CHATBOTS_SUCCESS:
            return {
                ...state,
                loading: LoadingStatus.LOADED,
                chatbots: action.payload
            }   
        case ChatBotActionTypes.LOAD_CHATBOTS_FAILURE:
            return {
                ...state,
                loading: LoadingStatus.ERROR,
                error: action.payload
            }
         case ChatBotActionTypes.CREATE_CHATBOT_SUCCESS:
            return {
                ...state,
                loading: LoadingStatus.LOADED,
                chatbots: [...state.chatbots, action.payload],
                chatbot: action.payload
            }
        case ChatBotActionTypes.CREATE_CHATBOT_FAILURE:
            return {
                ...state,
                loading: LoadingStatus.ERROR,
                error: action.payload
            }
        case ChatBotActionTypes.UPDATE_CHATBOT_SUCCESS:
            return {
                ...state,
                loading: LoadingStatus.LOADED,
                chatbot: action.payload,
                chatbots: state.chatbots.map(chatbot => chatbot.id === action.payload.id ? action.payload : chatbot)
            }
        case ChatBotActionTypes.UPDATE_CHATBOT_FAILURE:
            return {
                ...state,
                loading: LoadingStatus.ERROR,
                error: action.payload
            }
        case ChatBotActionTypes.DELETE_CHATBOT:
        return {
            ...state,
            loading: LoadingStatus.LOADING,
        }
        case ChatBotActionTypes.DELETE_CHATBOT_SUCCESS:
            return {
                ...state,
                loading: LoadingStatus.LOADED,
                chatbots: state.chatbots.filter(chatbot => chatbot.id !== action.payload)
            }
        case ChatBotActionTypes.DELETE_CHATBOT_FAILURE:
            return {
                ...state,
                loading: LoadingStatus.ERROR,
                error: action.payload
            }
        case ChatBotActionTypes.LOAD_CHATBOT:
            return {
                ...state,
                loading: LoadingStatus.LOADING,
            }
        case ChatBotActionTypes.LOAD_CHATBOT_SUCCESS:
            return {
                ...state,
                loading: LoadingStatus.LOADED,
                chatbot: action.payload
            }
        case ChatBotActionTypes.LOAD_CHATBOT_FAILURE:
            return {
                ...state,
                loading: LoadingStatus.ERROR,
                error: action.payload
            }
        case ChatBotActionTypes.LOAD_CHATBOT_DATA_CARACTERS_SUCCESS:
            return {
                ...state,
                loading: LoadingStatus.LOADED,
                chatbot_data_caracters: action.payload
            }
        case ChatBotActionTypes.LOAD_CHATBOT_DATA_CARACTERS_FAILURE:
            return {
                ...state,
                loading: LoadingStatus.ERROR,
                error: action.payload
            }
        case ChatBotActionTypes.CREATE_CHATBOT_ORIGIN:
            return {
                ...state,
                origins_loading: LoadingStatus.LOADING
            }
        case ChatBotActionTypes.CREATE_CHATBOT_ORIGIN_SUCCESS:
            return {
                ...state,
                origins_loading: LoadingStatus.LOADED,
                chatbot: state.chatbot ? {...state.chatbot, allowedOrigins: [...state.chatbot.allowedOrigins, action.payload]} : state.chatbot
            }
        case ChatBotActionTypes.CREATE_CHATBOT_ORIGIN_FAILURE:
            return {
                ...state,
                origins_loading: LoadingStatus.ERROR,
                error: action.payload
            }
        case ChatBotActionTypes.DELETE_CHATBOT_ORIGIN:
            return {
                ...state,
                origins_loading: LoadingStatus.LOADING
            }
        case ChatBotActionTypes.DELETE_CHATBOT_ORIGIN_SUCCESS:
            return {
                ...state,
                origins_loading: LoadingStatus.LOADED,
                chatbot: state.chatbot ? {...state.chatbot, allowedOrigins: state.chatbot.allowedOrigins.filter(origin => origin.origin != action.payload)} : state.chatbot
            }
        case ChatBotActionTypes.DELETE_CHATBOT_ORIGIN_FAILURE:
            return {
                ...state,
                origins_loading: LoadingStatus.ERROR,
                error: action.payload
            }
        

        default:
            return state;
    }
}