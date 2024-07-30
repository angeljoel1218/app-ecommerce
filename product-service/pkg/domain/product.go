package domain

import (
	"errors"
	"time"
)

var ErrProductNotFound = errors.New("product not found")
var ErrInternal = errors.New("service not available, try again")

type Product struct {
	ID          string     `json:"product_id"`
	Description string     `json:"description" validate:"required"`
	Price       float64    `json:"price" validate:"required"`
	Category    string     `json:"category" validate:"required"`
	CreatedAt   time.Time  `json:"created_at,omitempty"`
	UpdatedAt   time.Time  `json:"updated_at,omitempty"`
	DeletedAt   *time.Time `json:"deleted_at,omitempty"`
}

type ProductBuilder interface {
	SetId(id string) ProductBuilder
	SetDescription(description string) ProductBuilder
	SetPrice(price float64) ProductBuilder
	SetCategory(category string) ProductBuilder
	SetCreatedAt(createdAt time.Time) ProductBuilder
	SetUpdatedAt(updatedAt time.Time) ProductBuilder
	SetDeletedAt(deletedAt *time.Time) ProductBuilder
	Build() *Product
}

type productBuilderImpl struct {
	product *Product
}

func NewProductBuilder() ProductBuilder {
	return &productBuilderImpl{
		product: &Product{},
	}
}

func (p *productBuilderImpl) SetId(id string) ProductBuilder {
	p.product.ID = id
	return p
}

func (p *productBuilderImpl) SetDescription(description string) ProductBuilder {
	p.product.Description = description
	return p
}

func (p *productBuilderImpl) SetPrice(price float64) ProductBuilder {
	p.product.Price = price
	return p
}

func (p *productBuilderImpl) SetCategory(category string) ProductBuilder {
	p.product.Category = category
	return p
}

func (p *productBuilderImpl) SetCreatedAt(createdAt time.Time) ProductBuilder {
	p.product.CreatedAt = createdAt
	return p
}

func (p *productBuilderImpl) SetUpdatedAt(updatedAt time.Time) ProductBuilder {
	p.product.UpdatedAt = updatedAt
	return p
}

func (p *productBuilderImpl) SetDeletedAt(deletedAt *time.Time) ProductBuilder {
	p.product.DeletedAt = deletedAt
	return p
}

func (p *productBuilderImpl) Build() *Product {
	return p.product
}
