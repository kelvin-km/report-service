import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { Logger, ValidationPipe } from '@nestjs/common';
import { LoggingInterceptor } from './shared/utills/logging.interceptor';
import { json, urlencoded } from 'express';

const Port = process.env.PORT || 3000;

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  process.env.TZ = 'Africa/Nairobi';

  app.enableCors();
  await app.startAllMicroservices();
  app.setGlobalPrefix(process.env.PREFIX);
  app.useGlobalInterceptors(new LoggingInterceptor());
  app.useGlobalPipes(new ValidationPipe());
  app.use(json({ limit: '50mb' }));
  app.use(urlencoded({ limit: '50mb' }));
  await app.listen(Port);
  Logger.log(`🚀 API Service running at port ${Port}`, 'Bootstrap');
}
bootstrap();
