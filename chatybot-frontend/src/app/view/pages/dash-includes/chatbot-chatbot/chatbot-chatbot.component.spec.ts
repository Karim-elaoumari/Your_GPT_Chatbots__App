import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatbotChatbotComponent } from './chatbot-chatbot.component';

describe('ChatbotChatbotComponent', () => {
  let component: ChatbotChatbotComponent;
  let fixture: ComponentFixture<ChatbotChatbotComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChatbotChatbotComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChatbotChatbotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
