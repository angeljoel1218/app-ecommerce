import { InputType, Field } from '@nestjs/graphql';
import { IsNotEmpty, IsOptional, IsString } from 'class-validator';

@InputType()
export class CreateCustomerInput {
  @Field(() => String)
  @IsString()
  _id: string;

  @Field(() => String)
  @IsNotEmpty()
  @IsString()
  name: string;

  @Field(() => String)
  @IsNotEmpty()
  @IsString()
  lastName: string;

  @Field(() => String)
  @IsNotEmpty()
  @IsString()
  phone: string;

  @Field(() => String, { nullable: true })
  @IsString()
  @IsOptional()
  address?: string;
}
