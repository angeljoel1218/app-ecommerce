package handlers

import (
	"net/http"

	"github.com/gin-gonic/gin"

	bs "github.com/apexglobal/product-service/pkg/application/business"
	"github.com/apexglobal/product-service/pkg/application/config"
	"github.com/apexglobal/product-service/pkg/application/utils"
	model "github.com/apexglobal/product-service/pkg/domain"
)

type productHandler struct {
	productService bs.ProductService
}

func NewProductHandler(psrv bs.ProductService) *productHandler {
	return &productHandler{
		productService: psrv,
	}
}

func (h *productHandler) CreateHandler() gin.HandlerFunc {
	return func(ctx *gin.Context) {
		var req model.Product
		if err := ctx.BindJSON(&req); err != nil {
			ctx.JSON(http.StatusBadRequest, err.Error())
			return
		}

		if e := config.Valid.Struct(req); e != nil {
			ctx.JSON(http.StatusBadRequest, e.Error())
			return
		}

		res, err := h.productService.Create(ctx, req)

		if err != nil {
			ctx.JSON(http.StatusInternalServerError, err.Error())
			return
		}
		ctx.JSON(http.StatusCreated, utils.DataResponse(0, "User created", res))
	}
}

func (h *productHandler) GetProductHandler() gin.HandlerFunc {
	return func(ctx *gin.Context) {
		id := ctx.Param("id")

		res, err := h.productService.FindById(ctx, id)

		if err != nil {
			ctx.JSON(http.StatusInternalServerError, err.Error())
			return
		}

		ctx.JSON(http.StatusOK, utils.DataResponse(0, "Found product", res))
	}
}
