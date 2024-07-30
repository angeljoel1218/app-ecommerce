package impl

import (
	"context"
	"time"

	bs "github.com/apexglobal/product-service/pkg/application/business"
	"github.com/apexglobal/product-service/pkg/application/utils"
	model "github.com/apexglobal/product-service/pkg/domain"
	rep "github.com/apexglobal/product-service/pkg/domain/repository"
)

type ProductService struct {
	productRepository rep.ProductRepository
}

func NewProductService(productRepository rep.ProductRepository) bs.ProductService {
	return &ProductService{
		productRepository: productRepository,
	}
}

func (u *ProductService) Create(ctx context.Context, product model.Product) (*model.Product, error) {
	product.ID = utils.UID().New()
	product.CreatedAt = time.Now()
	product.UpdatedAt = time.Now()

	result, err := u.productRepository.Save(ctx, product)

	if err != nil {
		return nil, model.ErrInternal
	}

	if result == nil {
		return nil, model.ErrProductNotFound
	}

	return result, nil
}

func (u *ProductService) FindById(ctx context.Context, id string) (*model.Product, error) {
	return u.productRepository.FindById(ctx, id)
}
