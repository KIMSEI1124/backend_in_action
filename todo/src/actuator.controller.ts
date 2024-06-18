import { Controller, Get, Res } from '@nestjs/common';

export class HealthCheckResponse {
  status: string;
  server_time: string;

  constructor(isOk: boolean) {
    this.status = isOk ? 'Server Is OK' : 'Server Is Down';

    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');
    this.server_time = `${year}년 ${month}월 ${day}일 ${hours}시 ${minutes}분 ${seconds}초`;
  }

  public static isOk(status: boolean): HealthCheckResponse {
    return new HealthCheckResponse(status);
  }
}

@Controller('/actuator')
export class ActuatorController {
  @Get('/v1/health-check')
  healthCheck(): HealthCheckResponse {
    return HealthCheckResponse.isOk(true);
  }
}
