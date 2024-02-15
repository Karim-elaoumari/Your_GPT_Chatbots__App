import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  constructor(
  ) { }
  ngOnInit(): void {
    // this.activatedRoute.data.subscribe((response:any) =>{
    //   console.log("from home component");
    // },
    // (error:any) => {
    //   console.log(error);
    // }
    // );
  }
}
