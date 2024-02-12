export const reportopt = {
  path: '/app/lib/jasperreports-5.6.0',
  reports: {
    Receipt: {
      jasper: '/app/report/RECEIPT.jasper',
      conn: 'in_memory_json',
    },
    Statement: {
      jasper: '/app/report/statement/statement.jasper',
      conn: 'in_memory_json',
    },
  },
};
