import { createFeatureSelector, createSelector } from "@ngrx/store";
import { ChatbotState } from "./state";

const getChatBots  = (state: ChatbotState) => state.chatbots;
const getChatBot = (state: ChatbotState) => state.chatbot;
const getLoadingStatus = (state: ChatbotState) => state.loading;
const getError = (state: ChatbotState) => state.error;
const getOriginsLoadingStatus = (state: ChatbotState) => state.origins_loading;
// data caracters
const getChatBotDataCaracters = (state: ChatbotState) => state.chatbot_data_caracters;
const getChatBotState = createFeatureSelector<ChatbotState>('chatbots');
export const getOriginsLoadingStatusSelector = createSelector(getChatBotState, getOriginsLoadingStatus);
export const getChatBotsSelector = createSelector(getChatBotState, getChatBots);
export const getChatBotSelector = createSelector(getChatBotState, getChatBot);
export const getChatBotLoadingStatusSelector = createSelector(getChatBotState, getLoadingStatus);
export const getChatBotErrorSelector = createSelector(getChatBotState, getError);
export const getChatBotDataCaractersSelector = createSelector(getChatBotState, getChatBotDataCaracters);