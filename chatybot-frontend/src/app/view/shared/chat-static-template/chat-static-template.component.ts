import { Component, Input } from '@angular/core';
import { ChatBot } from 'src/app/core/models/ChatBot';

@Component({
  selector: 'app-chat-static-template',
  templateUrl: './chat-static-template.component.html',
  styleUrls: ['./chat-static-template.component.css']
})
export class ChatStaticTemplateComponent {
  constructor() {}
  @Input() chatbot:ChatBot = {} as ChatBot;
  ngOnInit() {
    
  }

}
