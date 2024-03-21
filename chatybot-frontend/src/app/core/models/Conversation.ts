import { ConversationEntry } from "./ConversationEntry";

export interface Conversation {
    id: string,
    userCode: string,
    domain: string,
    createdAt: Date,
    chatEntryHistories: ConversationEntry[]
}