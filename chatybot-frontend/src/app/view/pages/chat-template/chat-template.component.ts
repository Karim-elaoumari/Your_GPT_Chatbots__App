
import { Component, ElementRef, QueryList, ViewChildren } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ChatBot } from 'src/app/core/models/ChatBot';
import { Message } from 'src/app/core/models/Message';
import { QuestionRequest } from 'src/app/core/models/QuestionRequest';
import { ChatBotService } from 'src/app/core/services/ChatBotService';


@Component({
  selector: 'app-chat-template',
  templateUrl: './chat-template.component.html',
  styleUrls: ['./chat-template.component.css'],
})
export class ChatTemplateComponent {
  constructor( 
    private activatedRoute: ActivatedRoute,
    private chatBotService: ChatBotService,
    private formbuilder: FormBuilder,
    ) {}
  messageForm: FormGroup = this.formbuilder.group({message: ['', Validators.required]});
  chat_bot_id: string = '';
  chat_bot:ChatBot = {} as ChatBot;
  conversation_origin: string = '';
  loading:boolean = true;
  message_loading:boolean = false;
  messages:Message[] = [];
  conversation_code: string = '';
  @ViewChildren('messageContainer') messageContainer: QueryList<ElementRef> = {} as QueryList<ElementRef>;

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      this.chat_bot_id = params['ch'];
      this.conversation_origin = params['gn'].replace(/(^\w+:|^)\/\//, '');
      this.getChatBotInterface();
      this.createConversationCode();
    }
    );
    
  }
  getChatBotInterface() {
    this.chatBotService.getChatBotInterface(this.chat_bot_id, this.conversation_origin).subscribe(
      (data: any) => {
        this.chat_bot = data.data;
        this.loading=false;
      },
      (error:any) => {
        console.log(error);
      }
    );
  }
  onMessageSend() {
    if(this.message_loading==false && this.messageForm.valid){
      const message = this.messageForm.get('message');
      console.log('Submitted Message:', message?.value);
      let question_req:QuestionRequest = {
        question:message?.value,
        chatBotId:this.chat_bot_id,
        origin:this.conversation_origin,
        userCode:this.conversation_code
      } as QuestionRequest;
      this.message_loading = true;
      
      let user_message = { bot:false,message:message?.value} as Message;
      this.messages.push(user_message);
      this.messageForm.reset();
      this.chatBotService.sendMessage(question_req).subscribe(
        (response:any) => {
          this.message_loading = false;
          this.typingLiveMessage(response.data);
          
        },
        (error:any) => {
          console.log(error);
          let bot_message:Message  = { bot:true,message:"Error Try Agian"} as Message;
          this.messages.push(bot_message);
          this.message_loading = false;
        }
      )
    }
    
  
  }
  scrollToBottom() {
    setTimeout(() =>{
      const container = this.messageContainer.first.nativeElement;
      container.scrollTop = container.scrollHeight;
    });
  }

  removeMessages(){
    this.messages = [];
    this.conversation_code =  Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
    localStorage.removeItem('conv_code');
    localStorage.setItem('conv_code',this.conversation_code);
  }

  typingLiveMessage(message:string){
    let message_length = message.length;
    let time = message_length;
    let currentIndex = 0;
   console.log('Message Length:',message_length);
    let interval = setInterval(() => {
      if (currentIndex < message_length) {
        let bot_message: Message = {
          bot: true,
          message: message.substring(0, currentIndex + 1),
        } as Message;
  
        let last_message = this.messages[this.messages.length - 1];
  
        if (last_message && last_message.bot === true) {
          last_message.message = message.substring(0, currentIndex + 1);
        } else {
          this.messages.push(bot_message);
        }
  
        currentIndex++;
        this.scrollToBottom();
      } else {
        clearInterval(interval);
      }
    }, 30);
  }
  createConversationCode(){
    let conv_code = localStorage.getItem('conv_code');
    if(conv_code){
      this.conversation_code = conv_code;
    }else{
      this.conversation_code =  Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
      localStorage.setItem('conv_code',this.conversation_code);
    }
  }

}
