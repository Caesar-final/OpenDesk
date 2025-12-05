import { Component } from '@angular/core';
import { InputTextModule } from 'primeng/inputtext';
import {InputNumberModule} from 'primeng/inputnumber';
import {FloatLabel} from 'primeng/floatlabel';
import {Button} from 'primeng/button';

@Component({
  selector: 'app-login',
  imports: [InputTextModule, InputNumberModule, FloatLabel, Button],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

}
