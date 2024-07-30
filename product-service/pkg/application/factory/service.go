package factory

import (
	bs "github.com/apexglobal/product-service/pkg/application/business"
	"github.com/apexglobal/product-service/pkg/application/business/impl"
	"go.mongodb.org/mongo-driver/mongo"
)

type service interface {
	Create() service
}

type ProductService struct {
	db      *mongo.Database
	Service bs.ProductService
}

func (p ProductService) Create() service {
	prf := ProductRepositoryFactory{DB: p.db}
	rep := prf.Create().(ProductRepository)
	p.Service = impl.NewProductService(rep.Repository)
	return p
}
