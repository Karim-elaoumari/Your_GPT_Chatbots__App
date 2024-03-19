import { Component, SimpleChange } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { ChatBot } from 'src/app/core/models/ChatBot';
import { LoadChatBotDataCaracters } from 'src/app/state/chatbot/action';
import { getChatBotDataCaractersSelector, getChatBotSelector } from 'src/app/state/chatbot/selector';

@Component({
  selector: 'app-chatbot-chatbot',
  templateUrl: './chatbot-chatbot.component.html',
  styleUrls: ['./chatbot-chatbot.component.css']
})
export class ChatbotChatbotComponent {
  constructor(
    private store: Store<any>,
    ) { }
  chat_bot:ChatBot = {} as ChatBot;
  chat_bot_store:Observable<any> = this.store.select(getChatBotSelector);
  chat_bot_data_characters_store:Observable<any> = this.store.select(getChatBotDataCaractersSelector);
  data_characters:number = -1;
  ngOnInit(): void {
    this.getChatBotDetails();
    this.chat_bot_store.subscribe(
      (chat_bott:ChatBot) => {
        const iframe = document.getElementById('myIframe') as HTMLIFrameElement;
        if (iframe) {
          iframe.src = `${this.getChatbotScriptUrl()}`;
        }
      },
    );
  }
  getChatBotDetails(){
    this.chat_bot_store.subscribe(
      (chat_bott:ChatBot) => {
        this.chat_bot = chat_bott;
        if(this.chat_bot.id!=undefined){
          this.store.dispatch(new LoadChatBotDataCaracters(this.chat_bot.id));
    
        }
      },
      (error:any) => {
        console.log(error);
      }
    );
    
    this.chat_bot_data_characters_store.subscribe(
      (chat_bot_data_characters:any) => {
        this.data_characters = chat_bot_data_characters;
      },
      (error:any) => {
        console.log(error);
      }
    );
   
  }
  getChatbotScriptUrl(){
    return 'http://localhost:4200/chat-template?ch='+this.chat_bot.id+'&gn=http://localhost:4200'
  }


}
