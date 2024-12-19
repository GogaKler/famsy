export class EntityStateManager {
  private loadingStates: Record<string, number> = {};
  private errors: Record<string, string[]> = {};

  startLoading(entityKey: string, actionKey: string) {
    const key = `${entityKey}:${actionKey}`;
    if (!this.loadingStates[key]) {
      this.loadingStates[key] = 0;
    }
    this.loadingStates[key]++;
  }

  stopLoading(entityKey: string, actionKey: string) {
    const key = `${entityKey}:${actionKey}`;
    if (this.loadingStates[key] > 0) {
      this.loadingStates[key]--;
    }
  }

  isLoading(entityKey: string, actionKey: string): boolean {
    const key = `${entityKey}:${actionKey}`;
    return this.loadingStates[key] > 0;
  }

  addError(entityKey: string, actionKey: string, error: string) {
    const key = `${entityKey}:${actionKey}`;
    if (!this.errors[key]) {
      this.errors[key] = [];
    }
    this.errors[key].push(error);
  }

  getErrors(entityKey: string, actionKey: string): string[] {
    const key = `${entityKey}:${actionKey}`;
    return this.errors[key] || [];
  }

  clearErrors(entityKey: string, actionKey: string) {
    const key = `${entityKey}:${actionKey}`;
    if (this.errors[key]) {
      delete this.errors[key];
    }
  }

  clearAllErrors() {
    this.errors = {};
  }
}