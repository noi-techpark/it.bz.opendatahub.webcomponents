export class Sort {
  sortField: string;
  direction: string;

  constructor(sortField: string, direction: string) {
    this.sortField = sortField;
    this.direction = direction;
  }
}

export class PageRequest {
  size: number;
  number: number;
  sort: Sort[];

  constructor(size: number, number: number, sort: Sort[] = []) {
    this.size = size;
    this.number = number;
    this.sort = sort;
  }
}

export interface Page<T> {
  content: T[];

  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export class PageImpl<T> implements Page<T> {
  content: T[];

  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  size: number;
  totalElements: number;
  totalPages: number;

  constructor(content: T[], pageRequest: PageRequest, totalElements: number) {
    this.content = content;

    this.empty = content.length === 0;
    this.first = pageRequest.number === 0;
    this.last =
      content.length < pageRequest.size ||
      pageRequest.size * pageRequest.number + 1 > totalElements;
    this.number = pageRequest.number;
    this.numberOfElements = content.length;
    this.size = pageRequest.size;
    this.totalElements = totalElements;
    this.totalPages = Math.ceil(totalElements / pageRequest.size);
  }
}

export const Order = {
  ASC: 'asc',
  DESC: 'desc',
};
