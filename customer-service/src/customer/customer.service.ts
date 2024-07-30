import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { CreateCustomerInput } from './dto';
import { Customer } from './entities/customer.entity';

@Injectable()
export class CustomerService {
  constructor(
    @InjectRepository(Customer)
    private readonly customerRepository: Repository<Customer>,
  ) {}

  async create(createCustomerInput: CreateCustomerInput): Promise<Customer> {
    const newCustomer = this.customerRepository.create(createCustomerInput);
    return await this.customerRepository.save(newCustomer);
  }

  async findOne(id: string): Promise<Customer> {
    const customer = await this.customerRepository.findOneBy({
      _id: id,
    });

    if (!customer)
      throw new NotFoundException(`Customer with id: ${id} not found`);

    return customer;
  }
}
