import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { ChatBot } from 'src/app/core/models/ChatBot';
import { Conversation } from 'src/app/core/models/Conversation';
import { getChatBotSelector } from 'src/app/state/chatbot/selector';
import { LoadingStatus } from 'src/app/state/chatbot/state';
import { LoadConversations } from 'src/app/state/data/action';
import { getConvLoadingStatusSelector, getConversationsSelector } from 'src/app/state/data/selector';

@Component({
  selector: 'app-chatbot-log',
  templateUrl: './chatbot-log.component.html',
  styleUrls: ['./chatbot-log.component.css']
})
export class ChatbotLogComponent {
  constructor(private store: Store<any>,) { }
  page: number = 0;
  size: number = 10;
  chat_bot:ChatBot = {} as ChatBot;
  conversations:Conversation[] = [];
  loading: string = 'Waiting for Coversations to load...';
  selected_conversation:Conversation = {} as Conversation;
  ngOnInit() {
    this.store.select(getChatBotSelector).subscribe(
      (chat_bott:ChatBot) => {
        this.chat_bot = chat_bott;
        if(this.chat_bot.id!=undefined){
          this.getConversations();
        }
      }
    );
  }

  onPageChange(page: number) {
    this.page = page;
    this.getConversations();
  }
  getConversations() {
    this.store.dispatch(new LoadConversations({chat_bot_id:this.chat_bot.id, page:this.page, size:this.size}));
    this.store.select(getConversationsSelector).subscribe(
      (conversations:Conversation[]) => {
        this.conversations = conversations;
        this.store.select(getConvLoadingStatusSelector).subscribe(
          (loading:LoadingStatus) => {
            if(loading == LoadingStatus.LOADED){
              this.loading = '';
            }else if(loading == LoadingStatus.ERROR){
              this.loading = 'Error Loading Conversations';
            }
          }
        );

      }
    );
  }
  modalClicked(conversation:Conversation){
    this.selected_conversation = conversation;
  }
}
