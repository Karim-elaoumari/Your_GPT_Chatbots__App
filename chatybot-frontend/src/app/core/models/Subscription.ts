export interface subscription {
    id: number;
    name: string;
    textDataCharactersLimit: number;
    filesDataCharactersLimit: number;
    QuestionsAndAnswersLimit: number;
    dataCountLimit: number;
    userQuestionsLimitPerMonth: number;
    chatBotsLimit: number;
    initialDataCountLimit: number;
}