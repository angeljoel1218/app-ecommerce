package http

import (
	"context"
	"fmt"
	"log"
	"net/http"
	"os"
	"os/signal"
	"time"

	"github.com/apexglobal/product-service/pkg/application/config"
	"github.com/apexglobal/product-service/pkg/infrastructure/http/routes"
	"github.com/gin-gonic/gin"
	"go.mongodb.org/mongo-driver/mongo"
)

type Server struct {
	engine          *gin.Engine
	httpAddr        string
	shutdownTimeout time.Duration
	DB              *mongo.Database
}

func NewServer(ctx context.Context, db *mongo.Database) (context.Context, Server) {
	srv := Server{
		engine:          gin.New(),
		httpAddr:        fmt.Sprintf("%s:%d", config.Cfg.Host, config.Cfg.Port),
		shutdownTimeout: config.Cfg.ShutdownTimeout,
		DB:              db,
	}

	srv.routes()

	return serverContext(ctx), srv
}

func (s *Server) Run(ctx context.Context) error {
	log.Println("Server running on", s.httpAddr)

	srv := &http.Server{
		Addr:    s.httpAddr,
		Handler: s.engine,
	}

	go func() {
		if err := srv.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Fatal("server shut down", err)
		}
	}()

	<-ctx.Done()
	ctxShutDown, cancel := context.WithTimeout(context.Background(), s.shutdownTimeout)
	defer cancel()

	return srv.Shutdown(ctxShutDown)
}

func serverContext(ctx context.Context) context.Context {
	c := make(chan os.Signal, 1)
	signal.Notify(c, os.Interrupt)
	ctx, cancel := context.WithCancel(ctx)
	go func() {
		<-c
		cancel()
	}()

	return ctx
}

func (s *Server) routes() {
	list := routes.NewRoutes(s.DB)
	for _, r := range list {
		switch r.Method {
		case "POST":
			s.engine.POST(r.Path, r.Handler)
		case "PUT":
			s.engine.PUT(r.Path, r.Handler)
		case "PATCH":
			s.engine.PATCH(r.Path, r.Handler)
		case "DELETE":
			s.engine.DELETE(r.Path, r.Handler)
		case "GET":
			s.engine.GET(r.Path, r.Handler)
		}
	}
}
