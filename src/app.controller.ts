import { Controller, Get } from '@nestjs/common';
import { Public } from './features/authentication/auth-guard/decorator/public.decorator';

@Controller()
export class AppController {
  @Public()
  @Get('ping')
  async getPing(): Promise<any> {
    return 'pong';
  }
}
