import { TypeOrmModule } from '@nestjs/typeorm';
import { Module } from '@nestjs/common';

import { CustomerService } from './customer.service';
import { CustomerResolver } from './customer.resolver';
import { Customer } from './entities/customer.entity';

@Module({
  providers: [CustomerResolver, CustomerService],
  imports: [TypeOrmModule.forFeature([Customer])],
})
export class CustomerModule {}
