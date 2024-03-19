import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { ChatBot } from 'src/app/core/models/ChatBot';
import { CreateChatBot } from 'src/app/state/chatbot/action';
import { getChatBotErrorSelector, getChatBotLoadingStatusSelector, getChatBotSelector } from 'src/app/state/chatbot/selector';
import { LoadingStatus } from 'src/app/state/chatbot/state';

@Component({
  selector: 'app-new-chatbot',
  templateUrl: './new-chatbot.component.html',
  styleUrls: ['./new-chatbot.component.css']
})
export class NewChatbotComponent {
  constructor(private router:Router,
    private store: Store<any>
    ) { }
  new_chatbot_name:string = '';
  chat_bot_loading:Observable<any> = this.store.select(getChatBotLoadingStatusSelector)
  error="";
  success="";
  ngOnInit() {
  }
  createChatBot(){
    if(this.new_chatbot_name!="" && this.new_chatbot_name.length>3){
      this.store.dispatch(new CreateChatBot(this.new_chatbot_name));
      this.error="";
      this.success="Creating ChatBot...";
      this.chat_bot_loading.subscribe(
        (loading:LoadingStatus)=>{
          if(loading == LoadingStatus.LOADED){
            this.store.select(getChatBotSelector).subscribe(
              (chatbot:ChatBot)=>{
                if(chatbot.id){
                   window.location.href = '/chatbots/chatbot-details/'+chatbot.id;
                 }
              }
            )

          }else if(loading == LoadingStatus.ERROR ){
            this.success = "";
            this.error = "Error while creating chat bot You Have reached the maximum number of chat bots allowed. Please upgrade your plan to create more chat bots.";
          }
        }
      )
    }else{
      this.success="";
      this.error="chat bot name is required"
    }
  }

}
