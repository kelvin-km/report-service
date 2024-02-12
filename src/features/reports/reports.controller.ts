import { Body, Controller, Post, Request, Res } from '@nestjs/common';
import { Public } from '../authentication/auth-guard/decorator/public.decorator';
import { ReportsService } from './reports.service';
import { Response } from 'express';

@Controller('reports')
export class ReportsController {
  constructor(private readonly reportsService: ReportsService) {}

  @Public()
  @Post('receipt')
  async getPdfTransactionreceipt(
    @Res() response: Response,
    @Request() req,
    @Body() payload: any,
  ) {
    const report = await this.reportsService.getPdfTransactionreceipt();
    response.setHeader('Content-Description', 'File Transfer');
    response.setHeader('Content-Length', report.length);
    response.setHeader('Content-type', 'application/octet-stream');
    response.setHeader('Content-type', 'application/pdf');
    response.setHeader('Content-Type', 'application/force-download');
    response.setHeader('Content-disposition', 'attachment;filename=report.pdf');
    response.send(report);
  }
}
