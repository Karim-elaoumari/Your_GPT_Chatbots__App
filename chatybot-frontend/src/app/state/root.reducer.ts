
import { chatbotsReducer } from "./chatbot/reducer";
import { dataReducer } from "./data/reducer";

export const rootReducers = {
    chatbots: chatbotsReducer,
    data: dataReducer
  };
