import { HttpInterceptorFn } from '@angular/common/http';

export const customInterceptor: HttpInterceptorFn = (req, next) => {
  const localToken = localStorage.getItem("token");
  req = req.clone({headers: req.headers.set('Authorization','Bearer '+localToken)});
  console.log('Interceptor called');
  return next(req);
};
