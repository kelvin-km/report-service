import {
  Injectable,
  NestInterceptor,
  ExecutionContext,
  CallHandler,
  Logger,
} from '@nestjs/common';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable()
export class LoggingInterceptor implements NestInterceptor {
  intercept(context: ExecutionContext, next: CallHandler): Observable<any> {
    const now = Date.now();

    const req = context.switchToHttp().getRequest();
    return next.handle().pipe(
      tap(() => {
        if (req.method || req.url) {
          Logger.log(
            `${req.method} ${req.url} : ${Date.now() - now}ms`,
            context.getClass().name,
          );
        }
      }),
    );
  }
}
