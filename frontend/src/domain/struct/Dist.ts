import { DistSource } from './DistSource';

export interface Dist {
  basePath: string;

  files: Array<string>;
  scripts: Array<DistSource>;
}
