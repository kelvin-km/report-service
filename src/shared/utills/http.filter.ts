import {
  ArgumentsHost,
  Catch,
  ExceptionFilter,
  HttpException,
  Logger,
} from '@nestjs/common';
@Catch(HttpException)
export class HttpExceptionFilter implements ExceptionFilter {
  catch(exception: HttpException, host: ArgumentsHost) {
    const ctx = host.switchToHttp();

    const response = ctx.getResponse(),
      request = ctx.getRequest(),
      statusCode = exception.getStatus();

    const errorResponse = exception.getResponse();
    Logger.error(
      `${request.method} ${request.url}`,
      JSON.stringify(errorResponse),
      `Exception filter`,
    );
    return response.status(statusCode).json(errorResponse);
  }
}
