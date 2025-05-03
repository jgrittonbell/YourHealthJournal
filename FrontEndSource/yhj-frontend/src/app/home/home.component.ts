import { Component } from '@angular/core';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {
  login() {
    const { clientId, domain, redirectUri } = environment.cognito;
    const responseType = 'code';
    const scope = 'openid email';

    const loginUrl = `https://${domain}/login?client_id=${clientId}&response_type=${responseType}&scope=${scope}&redirect_uri=${redirectUri}`;

    window.location.href = loginUrl;
  }
}
