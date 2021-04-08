import { NuxtAxiosInstance } from '@nuxtjs/axios';
import { Sort, PageRequest } from './PagingAndSorting';

export default abstract class ARepository {
  protected basePath: string;
  protected $axios: NuxtAxiosInstance;
  protected ctx: any;
  protected defaultErrorCallback: any;

  protected constructor(ctx: any, defaultErrorCallback: any, basePath: string) {
    this.$axios = ctx.$axios;
    this.ctx = ctx;
    this.defaultErrorCallback = defaultErrorCallback;
    this.basePath = basePath;
  }

  $get(path: string, errorHandler: any): Promise<any> {
    return this.$axios.$get(`${this.basePath}${path}`).catch((error) => {
      this._handleError(error, errorHandler);
    });
  }

  $post(path: string, requestEntity: any, errorHandler: any): Promise<any> {
    return this.$axios
      .$post(`${this.basePath}${path}`, requestEntity)
      .catch((error) => {
        this._handleError(error, errorHandler);
      });
  }

  $put(path: string, requestEntity: any, errorHandler: any): Promise<any> {
    return this.$axios
      .$put(`${this.basePath}${path}`, requestEntity)
      .catch((error) => {
        this._handleError(error, errorHandler);
      });
  }

  $patch(path: string, requestEntity: any, errorHandler: any): Promise<any> {
    return this.$axios
      .$patch(`${this.basePath}${path}`, requestEntity)
      .catch((error) => {
        this._handleError(error, errorHandler);
      });
  }

  $delete(path: string, errorHandler: any): Promise<any> {
    return this.$axios.$delete(`${this.basePath}${path}`).catch((error) => {
      this._handleError(error, errorHandler);
    });
  }

  protected _handleError(error: any, customErrorCallback: any) {
    this.defaultErrorCallback(this.ctx, error, customErrorCallback);
  }

  pageQuery(pageRequest: PageRequest): string {
    if (!pageRequest) {
      return '';
    }

    return `pageSize=${pageRequest.size}&pageNumber=${
      pageRequest.number
    }${this.sortQuery(pageRequest.sort, true)}`;
  }

  filterQuery(filter: Object): string {
    if (!filter) {
      return '';
    }

    let result = '';

    for (const key in filter) {
      let value = filter[key];
      if (filter[key] !== undefined && filter[key] !== null) {
        value = value.toString();
        value = encodeURIComponent(value);
        result = result + `&${key}=${value || ''}`;
      }
    }

    return result.substr(1);
  }

  sortQuery(sort: Sort[], append: boolean = false): string {
    if (!sort) {
      return '';
    }

    let result = '';

    sort.forEach((elem) => {
      result =
        result +
        `&sort=${encodeURIComponent(elem.sortField + '|' + elem.direction)}`;
    });

    if (append) {
      return result;
    }
    return result.substr(1);
  }
}
