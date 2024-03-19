import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { UserResponse } from 'src/app/core/models/UserResponse';
import { TokenStorageService } from 'src/app/core/services/TokenStorageService';
import { LoadChatBots } from 'src/app/state/chatbot/action';
import { getChatBotLoadingStatusSelector, getChatBotsSelector } from 'src/app/state/chatbot/selector';
import { LoadingStatus } from 'src/app/state/chatbot/state';

@Component({
  selector: 'app-chatbots-list',
  templateUrl: './chatbots-list.component.html',
  styleUrls: ['./chatbots-list.component.css']
})
export class ChatbotsListComponent {
  constructor(
    private tokenStorageService:TokenStorageService,
    private store: Store<any>,
    private router:Router
    ) {}
  user:UserResponse = {} as UserResponse;
  my_chatbots = this.store.select(getChatBotsSelector);
  loaded: boolean = false;
  error: string = '';
  success: string = '';
  ngOnInit() {
    this.user = this.tokenStorageService.getUser();
    this.getMyChatBots();

  }
  getMyChatBots(){
    this.store.dispatch(new LoadChatBots());
    this.store.select(getChatBotLoadingStatusSelector).subscribe(
      (loading:LoadingStatus) => {
       if(loading == LoadingStatus.ERROR ){
          this.router.navigate(['/server-error']);
       }else if(loading == LoadingStatus.LOADED){
          this.loaded = true;
        }
      }
    )
      
  }
  viewChatBotDetails(chat_bot_id:string){
    window.location.href = '/chatbots/chatbot-details/'+chat_bot_id;
    
  }
  


}
