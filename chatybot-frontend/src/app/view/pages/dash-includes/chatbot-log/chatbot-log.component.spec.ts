import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatbotLogComponent } from './chatbot-log.component';

describe('ChatbotLogComponent', () => {
  let component: ChatbotLogComponent;
  let fixture: ComponentFixture<ChatbotLogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChatbotLogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChatbotLogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
