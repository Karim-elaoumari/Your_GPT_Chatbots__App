import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ChatBotService } from 'src/app/core/services/ChatBotService';
import { Store } from '@ngrx/store';
import { getChatBotLoadingStatusSelector, getChatBotSelector } from 'src/app/state/chatbot/selector';
import { Observable } from 'rxjs';
import { ChatBot } from 'src/app/core/models/ChatBot';
import { LoadChatBot } from 'src/app/state/chatbot/action';
import { LoadingStatus } from 'src/app/state/chatbot/state';

@Component({
  selector: 'app-chatbot-details',
  templateUrl: './chatbot-details.component.html',
  styleUrls: ['./chatbot-details.component.css']
})
export class ChatbotDetailsComponent {
  constructor(
    private router:Router,
    private route:ActivatedRoute,
    private chatBotService:ChatBotService,
    private store: Store<any>,
      ) {}
  chat_bot:ChatBot = {} as ChatBot;
  chat_bot_store:Observable<any> = this.store.select(getChatBotSelector);
  chat_bot_id: string = '';
  script_to_embed: string = '';
  loaded: boolean = false;
  
  ngOnInit() {
    
    this.route.params.subscribe(params => {
      this.chat_bot_id = params['ch'];
      if(this.chat_bot_id == ''){
        this.router.navigate(['/chatbots']);
      }
    }
    );
    this.getChatBotDetails();
  }
  getChatBotDetails(){
    this.store.dispatch(new LoadChatBot(this.chat_bot_id));
    this.store.select(getChatBotLoadingStatusSelector).subscribe(
      (loading:LoadingStatus) => {
       if(loading == LoadingStatus.ERROR){
          this.router.navigate(['/server-error']);
       }else if(loading == LoadingStatus.LOADED){
        console.log('loaded');
          this.loaded = true;
        }
      }
    )
    this.chat_bot_store.subscribe(
      (chat_bott:ChatBot) => {
        this.chat_bot = chat_bott;
        this.script_to_embed = '<script> \n var chat_bot_code = "'+this.chat_bot.id+'";\n</script> \n<script src="http://localhost:8081/chat-template-script.js"/>';
      }
    );

  }
}
