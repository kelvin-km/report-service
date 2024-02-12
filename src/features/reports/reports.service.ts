import { BadRequestException, Injectable, Logger } from '@nestjs/common';
import { reportopt } from '../../shared/config/report';
import { config, sampleData } from '../../shared/config/config';
import { ReceiptReportDto } from '../../shared/dto/request/receiptReport-dto';
const jasper = require('node-jasper')(reportopt);
@Injectable()
export class ReportsService {

  async getPdfTransactionreceipt(): Promise<any[]> {
    try {
      const data: Array<ReceiptReportDto> = sampleData.receipt;
      return await this.generatePdf(config.Report.RECEIPT, data, {});
    } catch (e) {
      Logger.log(JSON.stringify(e), `GET_TRANSACTION_RECEIPT_ERROR`);
      throw new BadRequestException(e.message || e.response.message);
    }
  }

  async generatePdf(reportJasper, data: any, parameter: any) {
    const report = {
      report: reportJasper,
      data: parameter,
      dataset: data,
    };
    return jasper.pdf(report);
  }
}
