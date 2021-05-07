export interface Options {
  key: string;
  type: string;
  required: boolean;
  label: string;
  options: any;
}

export interface Configuration {
  tagName: string;
  options: Array<Options>;
}
